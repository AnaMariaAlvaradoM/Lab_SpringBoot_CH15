const test = require("node:test");
const assert = require("node:assert/strict");
const { api, existe, leer, problemaDeUbicacion, todasLasClases } = require("./app");

function pom() {
  const ubicacion = problemaDeUbicacion();
  if (ubicacion) assert.fail(ubicacion);
  return leer("pom.xml").replace(/<parent>[\s\S]*?<\/parent>/, "");
}

test("el proyecto es Maven y está en la carpeta cuerpo-activo-api", () => {
  const ubicacion = problemaDeUbicacion();
  if (ubicacion) assert.fail(ubicacion);
  assert.ok(existe("mvnw") && existe("mvnw.cmd"), "Faltan mvnw y mvnw.cmd en cuerpo-activo-api: copia el proyecto completo del zip del Initializr");
});

test("Group co.cuerpoactivo y Artifact cuerpo-activo-api", () => {
  const xml = pom();
  const group = (xml.match(/<groupId>([^<]+)<\/groupId>/) || [])[1];
  const artifact = (xml.match(/<artifactId>([^<]+)<\/artifactId>/) || [])[1];
  assert.equal(group, "co.cuerpoactivo", `En el pom.xml, groupId debería ser co.cuerpoactivo y es ${group} (campo Group del Initializr)`);
  assert.equal(artifact, "cuerpo-activo-api", `En el pom.xml, artifactId debería ser cuerpo-activo-api y es ${artifact} (campo Artifact del Initializr)`);
});

test("tiene la dependencia Spring Web", () => {
  assert.match(pom(), /<artifactId>spring-boot-starter-webmvc<\/artifactId>/, "El pom.xml no tiene spring-boot-starter-webmvc: en el Initializr agrega la dependencia Spring Web");
});

test("tiene su clase principal con @SpringBootApplication", () => {
  const ubicacion = problemaDeUbicacion();
  if (ubicacion) assert.fail(ubicacion);
  const principal = todasLasClases().find((c) => /@SpringBootApplication/.test(c.codigo));
  assert.ok(principal, "No encuentro la clase con @SpringBootApplication: no borres CuerpoActivoApiApplication.java");
});

test("la aplicación compila y arranca", async () => {
  const direccion = await api();
  assert.match(direccion, /^http:\/\/localhost:\d+$/);
});
