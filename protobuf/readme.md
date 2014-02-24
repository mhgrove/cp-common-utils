From protobuf distro, installing it on your system

```bash
./configure
make
make check
sudo make install
```

When done, protoc should be on your path:

```bash
> protoc --version
libprotoc 2.4.1
```

To build Java libs, from protobuf distro

```bash
> cd java
> mvn test
> mvn package
```

For any of the `mvn` steps, you might need to increase the memory available to Maven: `export MAVEN_OPTS=-Xmx512m`

The 'test' target will run some protobuf test suite, presumably using your protobuf installation, so its
nice for maybe seeing if the install was correct.  Otherwise, running 'package' will get you the java lib;
protobuf jar will be in target directory (target/protobuf-java-xxx.jar) after maven is finished.
