# Test Collector Maven Plug-in

This project collects tests and uploads them to CJAN.org.

## Usage

In Eclipse, create a new execution with.

* Base directory:

`/home/kinow/Development/java/cjan/test-collector-maven-plugin/src/it/simple-it`

* Goal:

`-e -X clean test org.cjan:test-collector-maven-plugin:upload -Dreports=./target0/surefire-reports`

## License

MIT License
