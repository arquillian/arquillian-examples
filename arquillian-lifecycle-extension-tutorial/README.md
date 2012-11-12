Lifecycle Callback Extension 
========================================================

Extension showing how you can hook into the internal Arquillian lifecycle events and expose callbacks in your TestClass.

    @RunWith(Arquillian.class)
    public class MyTestCase {
    
        @Deployment
        public static WebArchive deploy() {
            return ShrinkWrap.create(WebArchive.class);
        }
    
        @BeforeDeploy
        public static void doBeforeDeploy() {}
    
        @AfterDeploy
        public static void doAfterDeploy() {}
    
        @BeforeUnDeploy
        public static void doBeforeUnDeploy() {}
    
        @AfterUnDeploy
        public static void doAfterUnDeploy() {}
    
        @Test
        public void shouldBeAbleTo() {}
    }

Following SPI's are used:

Client side
------------

* org.jboss.arquillian.core.spi.LoadableExtension
* Core @Observers
