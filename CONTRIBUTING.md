# Welcome!

Contributions are very welcome on `debezium-moneyconverter`. When contributing please keep this in mind:

- Open an issue to discuss new bigger features.
- Write code consistent with the project style and make sure the tests are passing.
- Stay in touch with us if we have follow up questions or requests for further changes.

# Development

This is a repository for Debezium connector custom money datatype converter.

## Tests

Tests and linting are run by
```shell
./gradlew test
```

## Static checking and Linting

Tests and linting are run by
```shell
./gradlew check
```

### Configuration

Money converter jar must be installed to Debezium connector path. See instructions from https://debezium.io/documentation/reference/stable/development/converters.html#configuring-and-using-converters
Jar can be built with and output will be in `build/libs/`
```shell
./gradlew assemble
```

The converter is configured to Debezium connector by adding these lines to Debezium connector configuration.
```properties
 converters: aivenMoneyConverter
 aivenMoneyConverter.type: io.aiven.kafka.connect.debezium.converters.MoneyConverter
```

# Opening a PR

- Commit messages should describe the changes, not the filenames. Win our admiration by following
  the [excellent advice from Chris Beams](https://chris.beams.io/posts/git-commit/) when composing
  commit messages.
- Choose a meaningful title for your pull request.
- The pull request description should focus on what changed and why.
- Check that the tests pass (and add test coverage for your changes if appropriate).
