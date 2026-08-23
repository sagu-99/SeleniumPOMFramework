# SeleniumPOMFramework
Selenium Page Object Model Framework

Quick run instructions

- To run tests with defaults from config.properties:
  mvn test -Dsurefire.suiteXmlFiles=testng.xml

- To override base URL or browser at runtime:
  mvn test -DbaseUrl="https://example.com/login" -Dbrowser=firefox -Dsurefire.suiteXmlFiles=testng.xml

- testng.xml contains two <test> entries (chrome, firefox). You can edit it or add new <test> blocks.

- Config precedence: System property > config.properties > built-in default.
