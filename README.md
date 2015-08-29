# Test Collector Maven Plug-in

This project collects tests and uploads them to [CJAN.org](http://cjan.org).

## Building

In Eclipse, create a new execution with.

* Base directory:

`/home/kinow/Development/java/cjan/test-collector-maven-plugin/src/it/simple-it`

* Goal:

`-e -X clean test org.cjan:test-collector:0.4-SNAPSHOT:upload -Dcjan.reports=./target0/surefire-reports -Dcjan.url=http://localhost:8000/upload/results`

Set up your token in settings.xml, as explained in CJAN.org.

## Set up

The plug-in is deployed to Apache Maven central repository. Edit your settings.xml to
configure the user that will authenticate with CJAN.org.

```
<servers>
  <server>
    <id>cjan</id>
    <username>$TOKEN</username>
  </server>
</servers>
```

## Usage

First generate test reports.

`mvn clean test`

Now you can upload your results to CJAN.org with the following command line:

`mvn org.cjan:test-collector:upload`

Other options that can be passed when using the plug-in include (with the default value):

* -Dcjan.reports=./target/surefire-reports
* -Dcjan.url=http://cjan.org/upload/results
* -Dcjan.proxy.host=
* -Dcjan.proxy.port=

## License

MIT License
