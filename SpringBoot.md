
@RequestMapping prend deux paramètres :
-
value qui sert à définir l’URL sur laquelle on peut atteindre la méthode.
-
Et method qui définit le verbe HTTP pour interroger l’URL.
-
De nouvelles annotations sont dorénavant disponibles, telles que @GetMapping, @PostMapping, @PutMapping, @DeleteMapping qui permettent de ne spécifier que l’URL, tout en utilisant le verbe HTTP lié, présent juste avant le mapping.
-
Dans ce code,  c'est l'annotation @GetMapping  qui permet de faire le lien entre l'URI "/Produits", invoquée via GET, et la méthode listeProduits.
-

![img_14.png](img_14.png)

Structure de fichier pour éxécuter le code, le @scan lis de haut en bas
-
![img_15.png](img_15.png)


------------

![img_16.png](img_16.png)

------------
@Repository 
-

cette annotation est appliquée à la classe afin d'indiquer à Spring qu'il s'agit d'une classe qui gère les données, ce qui nous permettra de profiter de certaines fonctionnalités
-
--------------------

@PostMapping 
-
              @PostMapping(value = "/Produits")
               public void ajouterProduit(@RequestBody Product product) {
               productDao.save(product);
                }
-----------------
@RequestBody 
-
Cette annotation demande à Spring que le JSON contenu dans la partie body de la requête HTTP soit converti en objet Java
-
![img_17.png](img_17.png)

----------
![img_18.png](img_18.png)

-----------
exemple d'API : 
-
        @RestController
        @RequestMapping("/api/book")

        public class BookController {

    @Autowired
    private BookRepository repository;

    @GetMapping("/{id}")
    public Book findById(@PathVariable long id) {
        return repository.findById(id)
            .orElseThrow(() -> new BookNotFoundException());
    }

    @GetMapping("/")
    public Collection<Book> findBooks() {
        return repository.getBooks();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book updateBook(
      @PathVariable("id") final String id, @RequestBody final Book book) {
        return book;
    }
    }
-------------
@Autowired :
-
L'annotation @Autowired permet d'activer l'injection automatique de dépendance. Cette annotation peut être placée sur un constructeur, une méthode setter ou directement sur un attribut (même privé).
-
-------------
@SpringBootApplication
-
Nous utilisons cette annotation pour marquer la classe principale d'une application Spring Boot :
-
    @SpringBootApplication
    class VehicleFactoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehicleFactoryApplication.class, args);
    }
    }
---------
@EnableAutoConfiguration
-
@EnableAutoConfiguration , comme son nom l'indique, permet la configuration automatique. Cela signifie que Spring Boot recherche les beans de configuration automatique sur son chemin de classe et les applique automatiquement.
-
---------------

@ConditionalOnClass et @ConditionalOnMissingClass
-
En utilisant ces conditions, Spring n'utilisera le bean d'auto-configuration marqué que si la classe dans l' argument de l'annotation est présente/absente :
-
----------------

@ConditionalOnBean et @ConditionalOnMissingBean
-
Nous pouvons utiliser ces annotations lorsque nous souhaitons définir des conditions basées sur la présence ou l'absence d'un bean spécifique :
-
         @Bean
         @ConditionalOnBean(name = "dataSource")
        LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        }

---------------

@ConditionalOnProperty
-
Avec cette annotation, nous pouvons faire des conditions sur les valeurs des propriétés :
-
    @Bean
    @ConditionalOnProperty(
    name = "usemysql",
    havingValue = "local"
    )
    DataSource dataSource() {
    // ...
    }
--------------

@ConditionalOnResource
-
Nous pouvons faire en sorte que Spring utilise une définition uniquement lorsqu'une ressource spécifique est présente :
-
    @ConditionalOnResource(resources = "classpath:mysql.properties" )
        Properties additionalProperties() {
        // ...
        }
--------------
@ConditionalOnWebApplication et @ConditionalOnNotWebApplication
-
Avec ces annotations, nous pouvons créer des conditions selon que l' application actuelle est ou non une application Web :
-
    @ConditionalOnWebApplication
    HealthCheckController healthCheckController() {
        // ...
        }

-------
@ConditionalExpression
-
Nous pouvons utiliser cette annotation dans des situations plus complexes. Spring utilisera la définition marquée lorsque l' expression SpEL sera évaluée à true :
-
        @Bean
        @ConditionalOnExpression("${usemysql} && ${mysqlserver == 'local'}")
        DataSource dataSource() {
        // ...
        }
-------------
@Conditionnel
-
Pour des conditions encore plus complexes, nous pouvons créer une classe évaluant la condition personnalisée . Nous indiquons à Spring d'utiliser cette condition personnalisée avec @Conditional :
-

        @Conditional(HibernateCondition.class)
        Properties additionalProperties() {
        //...
        }
------------------------

    public class RestTemplate
    extends InterceptingHttpAccessor
    implements RestOperations

Classe centrale de Spring pour l'accès HTTP côté client. Elle simplifie la communication avec les serveurs HTTP et applique les principes RESTful. Elle gère les connexions HTTP, laissant le code d'application fournir des URL (avec d'éventuelles variables de modèle) et extraire les résultats.
-
Les principaux points d'entrée de ce modèle sont les méthodes nommées d'après les six principales méthodes HTTP
-
![img_19.png](img_19.png)

------------------------
![img_20.png](img_20.png)

------------
La Java Persistence API (abrégée en JPA), est une interface de programmation Java permettant aux développeurs d'organiser des données relationnelles dans des applications utilisant la plateforme Java.
-
--------------

![img_22.png](img_22.png)

Désavantages microservices : 
-
compléxité de déploiement
-
------------
Qu’est ce qu’un micro service et comment est composé un micro service :
-
Ce sont des modules avec des api à part les unes des autres par services et qui peuvent être développé et maintenable à part
-
---------------
Pourquoi met on en place des micro services :
-
Pour la scalabilité du code.
-
------------
Quels sont les bienfaits d’une telle architecture
-
Les codes sont stockés à part, qui permet une moins grande compléxité du code et la garantie d'un code "stable".
-
------------------
Quels en sont les inconvénients
-
le déploiement est trop complexe
-
-----------------
Comment s’articule l’orchestration des services dans système complexe…
-
Par services, exemple :
-
service panier / service authentification / service chat / home ect..
-
![img_23.png](img_23.png)

![img_24.png](img_24.png)

-------------
![img_25.png](img_25.png)

------------------

les entités
-
sont représentées par des classes. Une classe agit comme un plan pour créer des objets de ce type. Chaque instance d'une classe devient une entité avec son propre ensemble d'attributs et de comportements.
-


