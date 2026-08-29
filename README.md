# SeleniumPOMFramework
Selenium Page Object Model Framework

Overview
This project is a Selenium + Java + TestNG automation framework built using the Page Object Model (POM). It is designed for maintainable UI automation with cross-browser support, data-driven testing, configurable runtime settings, parallel execution, and reporting.

Architecture
- base: lifecycle classes such as BaseTest and TestContext
- pages: page objects and reusable page actions
- utils: configuration, driver management, browser creation, Excel data access, waits, logging, and reporting
- tests: end-to-end test scenarios

Run commands
- Run with defaults from src/main/resources/config.properties:
  mvn test -Dsurefire.suiteXmlFiles=testng.xml

- Override browser and URL at runtime:
  mvn test -DbaseUrl="https://example.com/login" -Dbrowser=firefox -Dsurefire.suiteXmlFiles=testng.xml

- Run in headless mode:
  mvn test -Dheadless=true -Dsurefire.suiteXmlFiles=testng.xml

- Override environment/profile:
  mvn test -Denvironment=qa -Dprofile=default -Dsurefire.suiteXmlFiles=testng.xml

- Set retries explicitly:
  mvn test -Dmax.retries=2 -Dsurefire.suiteXmlFiles=testng.xml

- Parallel execution is already configured in testng.xml with thread-count.

Configuration precedence
System property > config.properties > built-in default

Available config values
- base.url
- default.browser
- environment
- profile
- implicit.wait.seconds
- explicit.wait.seconds
- headless
- max.retries

Example config.properties
```properties
base.url=https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
default.browser=chrome
environment=qa
profile=default
implicit.wait.seconds=10
explicit.wait.seconds=15
headless=false
max.retries=1
```

TestNG usage
- testng.xml contains multiple browser/test executions and a listener configuration.
- You can add new <test> blocks or parameters for browser, username, password, environment, and profile.

Reporting
- ExtentReports is used for HTML report generation.
- Screenshots are captured on failure and saved under the screenshots folder.

Best practices used in this framework
- ThreadLocal driver management for parallel safety
- Page Object Model for maintainability
- Data-driven test runs via Excel repository
- centralized configuration and runtime overrides
- listener-based reporting and retry support

Design patterns used in this framework

1. Page Object Model (POM)
   - Used in: LoginPage, HomePage, BasePage
   - Purpose: separate UI locators and actions from test logic to keep tests readable and maintainable.

2. Singleton / Holder Pattern
   - Used in: Config
   - Purpose: create one shared configuration instance for the lifetime of the framework.

3. Factory Pattern
   - Used in: BrowserFactory, DriverFactory
   - Purpose: centralize browser and driver creation logic instead of duplicating creation code across tests.

4. Strategy Pattern
   - Used in: BrowserOptionsFactory, WaitHelper
   - Purpose: define browser-specific and wait-specific strategies without hard-coded conditionals scattered across tests.

5. ThreadLocal / Context Pattern
   - Used in: DriverManager, TestContext
   - Purpose: isolate each test thread's WebDriver so parallel execution remains safe.

6. Template Method Pattern
   - Used in: BaseTest
   - Purpose: define the common lifecycle flow (setup > test > teardown) while allowing test classes to override or extend behavior.

7. Listener Pattern
   - Used in: FrameworkListener, MyListener (deprecated compatibility wrapper)
   - Purpose: handle reporting, failure screenshots, and suite-level lifecycle callbacks without mixing them into test code.

8. Repository Pattern / Data Access Pattern
   - Used in: ExcelDataRepository, TestDataProvider
   - Purpose: decouple test data loading from test logic and keep Excel parsing isolated and reusable.

9. Facade / Workflow Pattern
   - Used in: LoginPage.loginAs(...)
   - Purpose: expose a higher-level action that combines multiple page interactions into one meaningful workflow.

10. Builder-like Configuration Pattern
    - Used in: Config and BrowserOptionsFactory
    - Purpose: build runtime values and browser options using central rules rather than manually configuring them every time.
