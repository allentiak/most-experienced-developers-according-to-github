# Most Experienced Developers according to GitHub

FIXME: my new application.

## About

Code Retreat 2023 repo.


## Usage (verify this!)

FIXME: explanation

Run the project directly, via `:main-opts` (`-m allentiak.<main-ns>`):

    $ clojure -M:run-m
    Hello, World!

Run the project, overriding the name to be greeted:

    $ clojure -M:run-m Via-Main
    Hello, Via-Main!

Run the project's tests (they'll fail until you edit them):

    $ clojure -T:build test

Run the project's CI pipeline and build an uberjar (this will fail until you edit the tests to pass):

    $ clojure -T:build ci

This will produce an updated `pom.xml` file with synchronized dependencies inside the `META-INF`
directory inside `target/classes` and the uberjar in `target`. You can update the version (and SCM tag)
information in generated `pom.xml` by updating `build.clj`.

If you don't want the `pom.xml` file in your project, you can remove it. The `ci` task will
still generate a minimal `pom.xml` as part of the `uber` task, unless you remove `version`
from `build.clj`.

Run that uberjar:

    $ java -jar target/net.clojars.allentiak/<main-ns>-0.1.0-SNAPSHOT.jar

## Usage

Tests can be run from the terminal with:

    % clojure -X:test:test/run

(Without the first 'test', 'expectations' can not be found.)

REPL can be run from the terminal with:

    % clojure -M:test:repl/rebel-debug-refactor

## Options

FIXME: listing of options this app accepts.

## Examples

...

### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2023 Leandro Doctors

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

You should have received a copy of the GNU Affero General Public License along with this program.
If not, see <http://www.gnu.org/licenses/>.

### Additional Permission to convey with Clojure (under GNU AGPL version 3 section 7)

If you modify this Program, or any covered work, by linking or combining it with Clojure (or a modified version of that library), containing parts covered by the terms of the Eclipse Public License (EPL), the licensors of this Program grant you additional permission to convey the resulting work.
Corresponding Source for a non-source form of such a combination shall include the source code for the parts of Clojure used as well as that of the covered work.

### Warranty Disclaimer

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
See the GNU Affero General Public License for more details.
