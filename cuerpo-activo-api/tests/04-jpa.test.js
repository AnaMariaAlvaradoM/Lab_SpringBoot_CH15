const test = require("node:test");
const assert = require("node:assert/strict");
const { pedir, reiniciar, esperarEstado, esperarPlan, leer, buscarArchivo, exigirArchivo, problemaDeUbicacion } = require("./app");

function exigirProyecto() {
  const ubicacion = problemaDeUbicacion();
  if (ubicacion) assert.fail(ubicacion);
}

test("el pom.xml tiene Spring Data JPA y el driver de PostgreSQL", () => {
  exigirProyecto();
  const xml = leer("pom.xml");
  assert.match(xml, /<artifactId>spring-boot-starter-data-jpa<\/artifactId>/, "Falta la dependencia spring-boot-starter-data-jpa en el pom.xml");
  assert.match(xml, /<groupId>org\.postgresql<\/groupId>\s*<artifactId>postgresql<\/artifactId>/, "Falta la dependencia org.postgresql:postgresql en el pom.xml");
});

test("application.properties se conecta a PostgreSQL con ddl-auto=update", () => {
  exigirProyecto();
  const props = leer("src/main/resources/application.properties") || "";
  assert.match(props, /^\s*spring\.datasource\.url\s*=\s*jdbc:postgresql:\/\//m, "En application.properties falta spring.datasource.url=jdbc:postgresql://localhost:5432/cuerpo_activo");
  const ddl = (props.match(/^\s*spring\.jpa\.hibernate\.ddl-auto\s*=\s*(\S+)/m) || [])[1];
  assert.equal(ddl, "update", `spring.jpa.hibernate.ddl-auto debería ser update y es ${ddl || "(no está)"}: con create o create-drop se borran los datos al reiniciar`);
});

test("Plan es una entidad con @Id generado", () => {
  const codigo = exigirArchivo("Plan.java");
  assert.match(codigo, /@Entity\b/, "A Plan le falta @Entity (import jakarta.persistence.Entity)");
  assert.match(codigo, /@Id\b/, "A Plan le falta @Id sobre el atributo id");
  assert.match(codigo, /@GeneratedValue\b/, "A Plan le falta @GeneratedValue(strategy = GenerationType.IDENTITY) sobre el id");
});

test("PlanRepository extiende JpaRepository<Plan, Long>", () => {
  const codigo = exigirArchivo("PlanRepository.java");
  assert.match(codigo, /interface\s+PlanRepository\s+extends\s+JpaRepository\s*<\s*Plan\s*,\s*Long\s*>/, "PlanRepository debería declararse: public interface PlanRepository extends JpaRepository<Plan, Long>");
});

test("la lista en memoria ya no existe", () => {
  exigirProyecto();
  assert.ok(buscarArchivo("PlanRepository.java"), "Todavía no existe PlanRepository.java: primero haz la fase 2");
  assert.equal(buscarArchivo("PlanRepositoryEnMemoria.java"), null, "PlanRepositoryEnMemoria.java todavía existe: bórralo, ahora el repositorio lo escribe Spring Data JPA");
});

test("un plan creado sigue ahí después de reiniciar la API", async () => {
  const datos = { nombre: `Plan constancia ${Date.now() % 100000}`, duracionMeses: 3, precio: 300000 };
  const creado = await pedir("POST", "/api/planes", datos);
  esperarEstado(creado, [200, 201], "POST /api/planes");
  assert.ok(creado.json && Number.isInteger(creado.json.id), "POST /api/planes debería devolver el plan con su id");
  await reiniciar();
  const r = await pedir("GET", `/api/planes/${creado.json.id}`);
  assert.ok(r.status === 200 && r.json, `Después de reiniciar, GET /api/planes/${creado.json.id} ya no encuentra el plan: ¿tus datos siguen en memoria, o ddl-auto borra las tablas al arrancar?`);
  esperarPlan(r.json, { id: creado.json.id, ...datos }, `GET /api/planes/${creado.json.id} después de reiniciar`);
});
