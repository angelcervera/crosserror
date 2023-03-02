# Sample of cross build error

Working:
```shell
 sbt ++2.11.12 clean test
```

Working:
```shell
 sbt ++2.12.17 clean test
```

Working:
```shell
 sbt ++2.13.10 clean test
```

Not working:
```shell
 sbt +test
```
