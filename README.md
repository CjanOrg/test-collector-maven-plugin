# Test Collector Maven Plug-in

This project collects tests and uploads them to [CJAN.org](http://cjan.org).

## Building

In Eclipse, create a new execution with.

* Base directory:

`/home/kinow/Development/java/cjan/test-collector-maven-plugin/src/it/simple-it`

* Goal:

`-e -X clean test org.cjan:test-collector-maven-plugin:upload -Dcjan.reports=./target0/surefire-reports -Dcjan.token=${YOUR_TOKEN}`

## Usage

When testing your project, you can upload your results to CJAN.org with the following command line:

`mvn clean test org.cjan:test-collector-maven-plugin:upload -Dtoken=${YOUR_TOKEN}`

Other options that can be passed when using the plug-in include (with the default value):

* -Dcjan.reports=./target/surefire-reports
* -Dcjan.url=http://cjan.org/upload/results
* -Dcjan.proxy.host=
* -Dcjan.proxy.port=

## License

MIT License
