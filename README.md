Processit is a sample application using Akka, Camel, RabbitMQ and
Drools.  It takes an original JSON message from a RabbitMQ queue, logs
it, and sends it to a processing queue, where it is picked up by a
consumer that then processes it with Drools.

# Building

Projects can be built and packaged using Maven:

```
mvn package
```

# Running projects

Each project can be run using the following command:

```
java -jar target/<project-name>-<project-version>.jar
```

You can use `foreman` at the root of the project to get everything started:

```
foreman start
```

Cf. `Procfile` to see what processes are executed.

# Testing

A Ruby script under `test/` can be used to send a sample JSON message
to RabbitMQ and trigger the end-to-end process.

This script requires the `bunny` gem that needs to be installed first:

```
gem install bunny
```

The script can then be executed as follows:

```
ruby send_message.rb
```

# License

MIT. (c) 2016,2017 Sébastien Le Callonnec.
