// SPDX-License-Identifier: AGPL-3.0-or-later WITH GPL-3.0-linking-source-exception

# Most Experienced Developers according to GitHub

A data transformation pipeline project.


## About

A customer asks our consulting company for a developer with experience in a certain programming language. Write a tool so that the company can check which developer(s) already working at the company have such an experience, based on their GitHub profile information.

### Task 1

Create a database with data about the company members, their repos, and the corresponding programming languages they are proficient in.

### Task 2

Create a view with how many projects each colleague has implemented in a specific programming language.

Example:
- Employee 1
Java 12
Python 2

### Task 3 (optional)

Create a web application to search for a programming language and display the corresponding qualified developer(s), ranked.


## Usage

Run the project directly, via `:main-opts` (`-m allentiak.<main-ns>`):

    $ clojure -M:run-m

Run the project's tests:

    $ clojure -T:build test

Run the project's CI pipeline and build an uberjar:

    $ clojure -T:build ci

This will produce an updated `pom.xml` file with synchronized dependencies inside the `META-INF`
directory inside `target/classes` and the uberjar in `target`. You can update the version (and SCM tag)
information in generated `pom.xml` by updating `build.clj`.

If you don't want the `pom.xml` file in your project, you can remove it. The `ci` task will
still generate a minimal `pom.xml` as part of the `uber` task, unless you remove `version`
from `build.clj`.

Run that uberjar:

    $ java -jar target/net.clojars.allentiak/<main-ns>-0.1.0-SNAPSHOT.jar


## Hacking

Start a REPL from the terminal:

    % clojure -M:repl

Run tests on save:

    % clojure -X:watch-test


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
