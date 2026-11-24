// Ayuda para los tests: compila tu proyecto con Maven, lo arranca en un puerto libre
// y le hace peticiones HTTP, igual que Postman.
// Cada archivo de tests arranca su propia copia de tu API, con los datos iniciales.

const fs = require("node:fs");
const os = require("node:os");
const path = require("node:path");
const crypto = require("node:crypto");
const assert = require("node:assert/strict");
const { spawn, spawnSync } = require("node:child_process");

const RAIZ = path.join(__dirname, "..");
const PROYECTO = path.join(RAIZ, "cuerpo-activo-api");
const CODIGO = path.join(PROYECTO, "src", "main", "java");
const CACHE = path.join(os.tmpdir(), "cuerpo-activo-lab");
const IGNORAR = new Set(["target", ".idea", ".vscode", "node_modules", ".git", "HELP.md"]);
const EN_WINDOWS = process.platform === "win32";

const TIEMPO_COMPILAR_MS = 300000;
const TIEMPO_ARRANQUE_MS = 60000;
const TIEMPO_PETICION_MS = 5000;

// ---------- Dónde está el proyecto ----------

function problemaDeUbicacion() {
  if (fs.existsSync(path.join(PROYECTO, "pom.xml"))) return "";
  if (fs.existsSync(path.join(PROYECTO, "cuerpo-activo-api", "pom.xml"))) {
    return "Tu proyecto quedó dentro de otra carpeta (cuerpo-activo-api/cuerpo-activo-api): saca la carpeta interna a la raíz del repo (README, paso 3)";
  }
  if (["build.gradle", "build.gradle.kts"].some((a) => fs.existsSync(path.join(PROYECTO, a)))) {
    return "Tu proyecto es Gradle: en el Initializr elige Project: Maven y vuelve a generarlo (README, paso 3)";
  }
  if (!fs.existsSync(PROYECTO)) {
    return "No encuentro la carpeta cuerpo-activo-api en la raíz del repo: genera el proyecto en el Initializr con Artifact cuerpo-activo-api (README, paso 3)";
  }
  return "No encuentro cuerpo-activo-api/pom.xml: ¿descomprimiste el proyecto completo del Initializr?";
}

function archivosDelProyecto(carpeta = PROYECTO, base = "") {
  const lista = [];
  for (const entrada of fs.readdirSync(carpeta, { withFileTypes: true })) {
    if (IGNORAR.has(entrada.name)) continue;
    const relativa = path.posix.join(base, entrada.name);
    if (entrada.isDirectory()) lista.push(...archivosDelProyecto(path.join(carpeta, entrada.name), relativa));
    else if (entrada.isFile()) lista.push(relativa);
  }
  return lista.sort();
}

// ---------- Compilar con Maven ----------

function versionJava() {
  const proceso = spawnSync(EN_WINDOWS ? "java.exe" : "java", ["-version"], { encoding: "utf8", shell: false });
  return (proceso.stderr || "") + (process.env.JAVA_HOME || "");
}

function huella(archivos) {
  const hash = crypto.createHash("sha1");
  for (const archivo of archivos) {
    hash.update(archivo);
    hash.update(fs.readFileSync(path.join(PROYECTO, archivo)));
  }
  hash.update(fs.readFileSync(__filename));
  hash.update(versionJava());
  return hash.digest("hex");
}

function traducirMaven(texto) {
  const lineas = texto.split(/\r?\n/).map((l) => l.replace(/\u001b\[[0-9;]*m/g, ""));
  const compilacion = lineas.findIndex((l) => /\[ERROR\] .*\.java:\[\d+,\d+\] /.test(l));

  if (compilacion !== -1) {
    const [, archivo, numero, mensaje] = lineas[compilacion].match(/(\w+\.java):\[(\d+),\d+\] (.*)$/);
    const contexto = lineas.slice(compilacion, compilacion + 4).join("\n");
    const simbolo = (contexto.match(/symbol:\s+\w+\s+(\w+)/) || [])[1];
    let explicacion = mensaje;
    const nombresJpa = { buscarTodos: "findAll", buscarPorId: "findById", guardar: "save" };
    if (/';' expected/.test(mensaje)) explicacion = "falta un punto y coma (;)";
    else if (/cannot find symbol/.test(mensaje) && nombresJpa[simbolo]) {
      explicacion = `no encuentro «${simbolo}»: con JpaRepository ese método se llama ${nombresJpa[simbolo]}`;
    } else if (/is not abstract and does not override abstract method/.test(mensaje) && /EnMemoria/.test(archivo)) {
      explicacion = "esta clase ya no hace falta: bórrala, ahora el repositorio lo escribe Spring Data JPA";
    } else if (/cannot find symbol/.test(mensaje)) {
      explicacion = simbolo
        ? `no encuentro «${simbolo}»: ¿lo escribiste igual, con mayúsculas? ¿le falta el import?`
        : "usas algo que no está declarado o no tiene import: revisa los nombres";
    } else if (/package (\S+) does not exist/.test(mensaje)) {
      explicacion = `no existe el paquete ${mensaje.match(/package (\S+)/)[1]}: revisa el import (o falta la dependencia en el pom.xml)`;
    } else if (/incompatible types: (\S+) cannot be converted to (\S+)/.test(mensaje)) {
      const [, de, a] = mensaje.match(/incompatible types: (\S+) cannot be converted to (\S+)/);
      explicacion = `tipos incompatibles: un ${de} no se puede usar como ${a}`;
    } else if (/is not abstract and does not override abstract method (?:<\w+>)?(\w+)/.test(mensaje)) {
      const metodo = mensaje.match(/abstract method (?:<\w+>)?(\w+)/)[1];
      explicacion = `la clase dice implements, pero no escribió el método ${metodo}(...) de la interfaz`;
    } else if (/is public, should be declared in a file named/.test(mensaje)) {
      explicacion = "la clase debe llamarse igual que el archivo";
    } else if (/reached end of file while parsing/.test(mensaje)) {
      explicacion = "falta cerrar una llave }";
    } else if (/class, interface, enum, or record expected/.test(mensaje)) {
      explicacion = "hay una llave } de más o código por fuera de la clase";
    } else if (/missing return statement/.test(mensaje)) {
      explicacion = "a un método le falta el return";
    } else if (/method does not override or implement a method from a supertype/.test(mensaje)) {
      explicacion = "tiene @Override, pero ese método no existe en la interfaz: revisa el nombre y los parámetros";
    } else if (/unclosed string literal/.test(mensaje)) {
      explicacion = "falta cerrar unas comillas \"";
    } else if (/invalid method declaration; return type required/.test(mensaje)) {
      explicacion = "el constructor debe llamarse igual que la clase (y un método necesita su tipo de retorno)";
    }
    return `Tu proyecto no compila (${archivo}, línea ${numero}): ${explicacion}`;
  }

  const todo = lineas.join("\n");
  if (/release version (\d+) not supported/.test(todo)) {
    return `Tu Java es más viejo que el que pide el pom.xml (java.version ${todo.match(/release version (\d+)/)[1]}): instala el JDK 25 o revisa JAVA_HOME`;
  }
  if (/JAVA_HOME/.test(todo)) {
    return "Maven no encuentra Java: configura la variable JAVA_HOME (README, errores frecuentes)";
  }
  if (/'dependencies\.dependency\.version' for ([\w.:-]+) is missing/.test(todo)) {
    return `A la dependencia ${todo.match(/version' for ([\w.:-]+) is missing/)[1]} le falta su <version> en el pom.xml`;
  }
  if (/Non-parseable POM|Malformed POM|expected START_TAG or END_TAG/.test(todo)) {
    return "Tu pom.xml tiene un error de XML: revisa que cada etiqueta <...> tenga su cierre </...>";
  }
  if (/Could not resolve dependencies|Could not transfer artifact|Could not find artifact|Non-resolvable parent POM|Unknown host/.test(todo)) {
    const artefacto = (todo.match(/(?:Could not find artifact|artifact) ([\w.-]+:[\w.-]+)/) || [])[1];
    return `Maven no pudo descargar ${artefacto || "una dependencia"}: revisa tu internet y que el groupId, artifactId y version del pom.xml estén bien escritos`;
  }
  const primera = lineas.find((l) => /^\[ERROR\]\s+\S/.test(l)) || lineas.find((l) => l.trim()) || "error desconocido";
  return `Maven no pudo compilar tu proyecto: ${primera.replace(/^\[ERROR\]\s*/, "").slice(0, 250)}`;
}

function comandoMaven(carpeta) {
  const args = ["-B", "-q", "-Dstyle.color=never", "-Dmaven.test.skip=true", "package"];
  if (EN_WINDOWS && fs.existsSync(path.join(carpeta, "mvnw.cmd"))) {
    return { programa: "mvnw.cmd " + args.join(" "), args: [], shell: true };
  }
  if (!EN_WINDOWS && fs.existsSync(path.join(carpeta, "mvnw"))) {
    return { programa: "sh", args: ["mvnw", ...args], shell: false };
  }
  return { programa: EN_WINDOWS ? "mvn.cmd " + args.join(" ") : "mvn", args: EN_WINDOWS ? [] : args, shell: EN_WINDOWS };
}

function copiarProyecto(destino) {
  for (const archivo of archivosDelProyecto()) {
    const final = path.join(destino, archivo);
    fs.mkdirSync(path.dirname(final), { recursive: true });
    fs.copyFileSync(path.join(PROYECTO, archivo), final);
  }
}

function buscarJar(carpeta) {
  const target = path.join(carpeta, "target");
  if (!fs.existsSync(target)) return "";
  const jar = fs.readdirSync(target).find((a) => a.endsWith(".jar"));
  return jar ? path.join(target, jar) : "";
}

// Compila tu proyecto una sola vez: si no cambiaste nada, reutiliza el resultado.
function construir() {
  const ubicacion = problemaDeUbicacion();
  if (ubicacion) return { error: ubicacion };

  const destino = path.join(CACHE, huella(archivosDelProyecto()));
  if (fs.existsSync(path.join(destino, "error.txt"))) return { error: fs.readFileSync(path.join(destino, "error.txt"), "utf8") };
  if (fs.existsSync(path.join(destino, "app.jar"))) return { jar: path.join(destino, "app.jar") };

  fs.mkdirSync(CACHE, { recursive: true });
  const temporal = fs.mkdtempSync(path.join(CACHE, "tmp-"));
  const fuente = path.join(temporal, "fuente");
  copiarProyecto(fuente);

  const maven = comandoMaven(fuente);
  const env = { ...process.env, MAVEN_OPTS: `${process.env.MAVEN_OPTS || ""} -Duser.language=en -Duser.country=US`.trim() };
  const proceso = spawnSync(maven.programa, maven.args, {
    cwd: fuente, encoding: "utf8", env, shell: maven.shell, timeout: TIEMPO_COMPILAR_MS, maxBuffer: 16 * 1024 * 1024,
  });

  let resultado;
  if (proceso.error && proceso.error.code === "ENOENT") {
    resultado = { error: "No encuentro Maven: falta el archivo mvnw en cuerpo-activo-api (viene en el zip del Initializr)" };
  } else if (proceso.error && proceso.error.code === "ETIMEDOUT") {
    fs.rmSync(temporal, { recursive: true, force: true });
    return { error: "Maven tardó más de 5 minutos: revisa tu internet y vuelve a intentar" };
  } else if (proceso.status === 0 && buscarJar(fuente)) {
    fs.copyFileSync(buscarJar(fuente), path.join(temporal, "app.jar"));
    resultado = { jar: path.join(destino, "app.jar") };
  } else {
    resultado = { error: traducirMaven((proceso.stdout || "") + "\n" + (proceso.stderr || "")) };
    fs.writeFileSync(path.join(temporal, "error.txt"), resultado.error);
  }

  fs.rmSync(fuente, { recursive: true, force: true });
  try {
    fs.renameSync(temporal, destino);
  } catch {
    fs.rmSync(temporal, { recursive: true, force: true });
  }
  return resultado;
}

// ---------- Arrancar la aplicación ----------

function traducirArranque(log) {
  if (/Failed to configure a DataSource: 'url' attribute is not specified/.test(log)) {
    return "La app no arrancó: agregaste JPA pero falta spring.datasource.url en application.properties";
  }
  const clave = log.match(/password authentication failed for user "([^"]+)"/);
  if (clave) return `La app no arrancó: PostgreSQL rechazó la contraseña del usuario ${clave[1]}: revisa spring.datasource.password`;
  const base = log.match(/database "([^"]+)" does not exist/);
  if (base) return `La app no arrancó: la base de datos ${base[1]} no existe; créala en pgAdmin`;
  if (/Connection to [\w.]+:\d+ refused/.test(log)) {
    return "La app no arrancó: PostgreSQL no responde en ese puerto; ¿está encendido el servicio de PostgreSQL?";
  }
  const sinId = log.match(/Entity '([\w.$]+)' has no identifier/);
  if (sinId) return `La app no arrancó: la entidad ${sinId[1].split(".").pop()} no tiene @Id`;
  const noEntidad = log.match(/Not a managed type: class ([\w.$]+)/);
  if (noEntidad) return `La app no arrancó: ${noEntidad[1].split(".").pop()} no tiene @Entity, pero hay un repositorio JPA para ella`;
  const constructor = log.match(/Parameter \d+ of constructor in ([\w.$]+) required a bean of type '([\w.$]+)' that could not be found/);
  if (constructor) {
    const [, quien, que] = constructor.map((t) => t.split(".").pop());
    return `La app no arrancó: el constructor de ${quien} pide un ${que} y Spring no tiene ninguno. ¿Le pusiste @Service o @Repository a la clase que lo implementa?`;
  }
  const varios = log.match(/required a single bean, but (\d+) were found/);
  if (varios) {
    return `La app no arrancó: Spring encontró ${varios[1]} clases que sirven para el mismo constructor y no sabe cuál usar. Deja solo una con @Repository (o @Service)`;
  }
  const ambigua = log.match(/Ambiguous mapping\. Cannot map '(\w+)' method/);
  if (ambigua) return "La app no arrancó: dos métodos tienen la misma ruta y el mismo método HTTP (Ambiguous mapping)";
  if (/UnsupportedClassVersionError/.test(log)) return "Tu java y el JDK que usa Maven son de versiones distintas: revisa JAVA_HOME y el PATH";
  const descripcion = log.match(/Description:\s*\r?\n\s*\r?\n(.+)/);
  if (descripcion) return `La app no arrancó: ${descripcion[1].trim()}`;
  const causas = [...log.matchAll(/Caused by: ([\w.$]+): (.+)/g)];
  if (causas.length) {
    const [, tipo, mensaje] = causas[causas.length - 1];
    return `La app no arrancó: ${tipo.split(".").pop()}: ${mensaje.trim().slice(0, 200)}`;
  }
  return "La app arrancó y se cerró sola: ¿el proyecto tiene la dependencia Spring Web (spring-boot-starter-webmvc)?";
}

let proceso = null;
let arranque = null;

// En GitHub, el robot usa su propia base PostgreSQL (variables LAB_DB_*).
// En tu computador se usa la de tu application.properties.
function argumentosBaseDeDatos() {
  if (!process.env.LAB_DB_URL) return [];
  return [
    `--spring.datasource.url=${process.env.LAB_DB_URL}`,
    `--spring.datasource.username=${process.env.LAB_DB_USER || "postgres"}`,
    `--spring.datasource.password=${process.env.LAB_DB_PASSWORD || "postgres"}`,
  ];
}

function detener() {
  if (proceso && proceso.exitCode === null) proceso.kill(EN_WINDOWS ? undefined : "SIGKILL");
  proceso = null;
}
process.on("exit", detener);
process.on("SIGTERM", () => { detener(); process.exit(1); });

function arrancarApp() {
  const compilacion = construir();
  if (compilacion.error) return Promise.reject(new Error(compilacion.error));

  return new Promise((resolver, rechazar) => {
    let log = "";
    let listo = false;
    proceso = spawn(EN_WINDOWS ? "java.exe" : "java", [
      "-XX:TieredStopAtLevel=1", "-Dfile.encoding=UTF-8", "-Duser.language=en", "-Duser.country=US",
      "-jar", compilacion.jar, "--server.port=0", "--spring.main.banner-mode=off", ...argumentosBaseDeDatos(),
    ], { cwd: os.tmpdir(), stdio: ["ignore", "pipe", "pipe"] });

    const reloj = setTimeout(() => {
      detener();
      rechazar(new Error("La app tardó más de 60 s en arrancar"));
    }, TIEMPO_ARRANQUE_MS);

    const leer = (trozo) => {
      log += trozo.toString();
      const puerto = log.match(/Tomcat started on port (\d+)/);
      if (puerto && !listo) {
        listo = true;
        clearTimeout(reloj);
        resolver(`http://localhost:${puerto[1]}`);
      }
    };
    proceso.stdout.on("data", leer);
    proceso.stderr.on("data", leer);
    // Para que el archivo de tests termine solo: al salir, process.on("exit") detiene la API.
    proceso.unref();
    proceso.stdout.unref();
    proceso.stderr.unref();
    proceso.on("error", () => {
      clearTimeout(reloj);
      rechazar(new Error("No encuentro Java (java) en tu computador: revisa JAVA_HOME y el PATH"));
    });
    proceso.on("exit", () => {
      clearTimeout(reloj);
      if (!listo) rechazar(new Error(traducirArranque(log)));
    });
  });
}

// Arranca tu API la primera vez que un test la necesita (una vez por archivo de tests)
// y la detiene cuando terminan los tests de ese archivo.
function api() {
  if (!arranque) {
    arranque = arrancarApp();
    arranque.catch(() => {});
  }
  return arranque;
}

// Apaga tu API y la vuelve a arrancar (como detenerla y darle ▶ en IntelliJ).
async function reiniciar() {
  await api();
  const anterior = proceso;
  detener();
  if (anterior && anterior.exitCode === null) {
    await new Promise((listo) => {
      const reloj = setTimeout(listo, 15000);
      anterior.once("exit", () => {
        clearTimeout(reloj);
        listo();
      });
    });
  }
  arranque = null;
  return api();
}

// ---------- Peticiones ----------

async function pedir(metodo, ruta, cuerpo) {
  const base = await api();
  const opciones = { method: metodo, signal: AbortSignal.timeout(TIEMPO_PETICION_MS), headers: {} };
  if (cuerpo !== undefined) {
    opciones.headers["Content-Type"] = "application/json";
    opciones.body = JSON.stringify(cuerpo);
  }
  let respuesta;
  try {
    respuesta = await fetch(base + ruta, opciones);
  } catch (error) {
    if (error.name === "TimeoutError") assert.fail(`${metodo} ${ruta} tardó más de 5 s en responder: ¿un ciclo que no termina?`);
    assert.fail(`${metodo} ${ruta} no obtuvo respuesta: la app se detuvo (${error.message})`);
  }
  const texto = await respuesta.text();
  let json;
  try {
    json = texto ? JSON.parse(texto) : undefined;
  } catch {
    json = undefined;
  }
  return { status: respuesta.status, texto, json, headers: respuesta.headers };
}

// ---------- Ayudas para comparar ----------

function esperarEstado(r, esperado, peticion, pista = "") {
  const extra = pista ? ` (${pista})` : "";
  assert.ok(
    [].concat(esperado).includes(r.status),
    `${peticion} debería responder ${[].concat(esperado).join(" o ")} y respondió ${r.status}${extra}`
  );
}

// Revisa que un plan del JSON tenga exactamente los datos esperados.
function esperarPlan(plan, esperado, peticion, pista = "") {
  const extra = pista ? ` (${pista})` : "";
  assert.ok(plan && typeof plan === "object" && !Array.isArray(plan), `${peticion} debería traer un plan en JSON y trajo: ${JSON.stringify(plan) ?? "nada"}`);
  for (const [propiedad, valor] of Object.entries(esperado)) {
    const getter = "get" + propiedad[0].toUpperCase() + propiedad.slice(1) + "()";
    assert.ok(propiedad in plan, `${peticion}: al plan le falta la propiedad «${propiedad}» (¿el getter se llama ${getter}?)`);
    assert.equal(plan[propiedad], valor, `${peticion}: «${propiedad}» debería ser ${JSON.stringify(valor)} y es ${JSON.stringify(plan[propiedad])}${extra}`);
  }
}

// ---------- Leer tu código ----------

function existe(relativa) {
  return fs.existsSync(path.join(PROYECTO, relativa));
}

function leer(relativa) {
  const ruta = path.join(PROYECTO, relativa);
  return fs.existsSync(ruta) ? fs.readFileSync(ruta, "utf8") : null;
}

// Busca un archivo .java por su nombre en cualquier paquete de src/main/java.
function buscarArchivo(nombre, carpeta = CODIGO) {
  if (!fs.existsSync(carpeta)) return null;
  for (const entrada of fs.readdirSync(carpeta, { withFileTypes: true })) {
    const ruta = path.join(carpeta, entrada.name);
    if (entrada.isDirectory()) {
      const encontrado = buscarArchivo(nombre, ruta);
      if (encontrado) return encontrado;
    } else if (entrada.name === nombre) {
      return ruta;
    }
  }
  return null;
}

function sinComentarios(texto) {
  return texto.replace(/\/\*[\s\S]*?\*\//g, "").replace(/\/\/.*$/gm, "");
}

// Código de una clase (sin comentarios), en el paquete que sea. null si no existe.
function codigoSinComentarios(nombreArchivo) {
  const ruta = buscarArchivo(nombreArchivo);
  return ruta ? sinComentarios(fs.readFileSync(ruta, "utf8")) : null;
}

function exigirArchivo(nombreArchivo) {
  const ubicacion = problemaDeUbicacion();
  if (ubicacion) assert.fail(ubicacion);
  const codigo = codigoSinComentarios(nombreArchivo);
  if (codigo === null) assert.fail(`No encuentro ${nombreArchivo} en src/main/java: revisa que el archivo se llame exactamente así`);
  return codigo;
}

// Todas las clases del proyecto (sin comentarios), para buscar una anotación.
function todasLasClases(carpeta = CODIGO) {
  if (!fs.existsSync(carpeta)) return [];
  const lista = [];
  for (const entrada of fs.readdirSync(carpeta, { withFileTypes: true })) {
    const ruta = path.join(carpeta, entrada.name);
    if (entrada.isDirectory()) lista.push(...todasLasClases(ruta));
    else if (entrada.name.endsWith(".java")) lista.push({ nombre: entrada.name, codigo: sinComentarios(fs.readFileSync(ruta, "utf8")) });
  }
  return lista;
}

module.exports = {
  construir, pedir, api, reiniciar, esperarEstado, esperarPlan, problemaDeUbicacion,
  existe, leer, buscarArchivo, codigoSinComentarios, exigirArchivo, todasLasClases,
};
