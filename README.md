1- The problems in the application:

![123](https://user-images.githubusercontent.com/120324997/226065987-63ec8a7c-7025-418b-9f27-45e793bf51b4.png)
![1234](https://user-images.githubusercontent.com/120324997/226065992-850e852c-f1f3-4142-97ec-572fd8884ab2.png)
![12345](https://user-images.githubusercontent.com/120324997/226065993-d9ca1d9a-8705-4f90-89a5-00aa562930a5.png)


2- Categorize the issues found by SonarQube and the explain how these issues affect the software quality: 
Editor file :
—---------------------------------------------------
ahmad 
(147, 13): Cognitive complexity of methods should not be too high
its critical code smell, where it could impact the behavior of the application in production.
  Maintenance: it will be difficult to maintain and to be read or understand complex methods which will cost in development and may cause bugs.
 
(158, 46) (158, 92) (169, 37): string literals should not be duplicated.
Its critical code smell, where it could impact the behavior of the application in production.
 Maintainability: duplicated code cause difficulty in updating all occurrences which will consume time and make it hard to reusable the code.
 
(298, 2): A reference to null should never be dereferenced.
Its major bug where a quality flaw that can highly impact in production.
§  Reliability: to deal with unexpected failures, it should avoid null values before dereferencing pointers.
 
(181, 8): conditionally executed code should be reachable 
Its major bug where a quality flaw that can highly impact in production.
Conditional expressions which are always true or false can lead to dead code.
§  Maintainability: difficulty in modifying and maintaining the code will decrease the maintainability rate.
§  Flexibility: Software is less adaptable which cause difficulty in changing requirements.

(166, 5), (196, 4): standard outputs should not be used directly to log anything.
Its major code smell where a quality flaw that can highly impact the developer's productivity.
§  Maintainability: difficulty in tracking log messages and editing them cause less code maintainability.
§  Testability: difficulty in capturing and analyzing during the testing phase introduce problems in debugging in this phase.
 
(199, 16): generic exceptions should never be thrown
Its major bug where a quality flaw that can highly impact in production.
§  Maintainability: difficulty in analyzing issues and maintaining them cause time consuming and more work cost in maintaining.
 
(26, 15): multiple variables should not be declared on the same line.
Its major bug where a quality flaw that can highly impact in production.
§  Maintainability:  difficulty in understanding and reading the code will make it hard to edit and maintain it whenever needed.

From FindDilaoge file:


(22, 8): Chile class fields should not shadow parent class fields.
Its blocker code smell.
  Maintainability:  child and parent classes has same field name cause confusion and could introduce bugs and unexpected behavior which will make it hard to maintain or edit the code.
 
(26, 15): multiple variables should not be declared on the same line.
Its major bug where a quality flaw that can highly impact in production.
 Maintainability: it introduces difficulty in reading and understanding the code as editing any variable may require editing the entire line.




  








ahmad
—-------------------------------------------------------------------------------------------7


11-   (67,14) Code smell, minor. Method name does not comply with a naming convention, Shared naming conventions allow teams to collaborate efficiently. This rule checks that all method names match a provided regular expression.
Refer : Maintainability.

12- (252, 2) Code smell, Major. Use Standard output to log message. When logging a message there are several important requirements which must be fulfilled:
The user must be able to easily retrieve the logs
The format of all logged message must be uniform to allow the user to easily read the log
Logged data must actually be recorded
Sensitive data must only be logged securely
If a program directly writes to the standard outputs, there is absolutely no way to comply with those requirements.
Refer :Maintainability,testability .

13- (227,14) Refactor this method to reduce its Cognitive Complexity from 26 to the 15 allowed.
code smell, major. Cognitive Complexity too high in method, it is a measure of how hard the control flow of a method is to understand. Methods with high Cognitive Complexity will be difficult to maintain.
refer :

14 - (238,9) Condition 'changed' is always 'true' 
 bug, major.Conditional expression is always true and leads to dead code. Such code is always buggy and should never be used in production.
Refer 
15- (256,17) Define and throw a dedicated exception instead of using a generic one.
Code smell, major. Throw generic exceptions. Using such generic exceptions as Error, RuntimeException, Throwable, and Exception prevents calling methods from handling true, system-generated exceptions differently than application-generated errors.
Refer   









findDialog file : 
1 - (22,8) 
:Product revision
(26,15)multiple variables should not be declared on the same line. testability and maintainability

2-(26,15)  its major  bug where a quality flaw that can highly impact the developer's productivity, multiple variables should not be declared on the same line,Declaring multiple variables on one line is difficult to read.

3- (116, 13) Code smell, Critical. Empty method may confuse other developers or anyone who reads the code later, they may wonder why you have an empty method and what it is supposed to do.

4- (84,7) Code smell, major. commented out lines of code bloats programs and reduces readability.

—----------------------------------------------------------------------------------------------------------------------
Mosaab
(3, 7),(7, 7),(18, 7),(19,7): unnecessary imports should be removed.
Its minor code smell
Maintainability: it causes difficulty in read and understand the code for developers.

File editorException and editorfileException and EditorSaveAsException:
(5, 13),(3,13),(3,13): Inheritance tree of classes should not be too deep.
Its major code smell.
Maintainability: it causes difficulty in maintaining the code as it affects all related classes and causes difficulty in following resulting effect in all of them which could introduce many bugs.
Testability: difficulty in software testing which consumes time and effort.
 
File findDialog: 
(28, 9): .fields in a “Serializable” class should either be transient or serializable
Its critical code smell.
Reliability: An exception will be thrown, if a field is not serializable.
If a non-serializable field is not being transient, means it will be included in the serialized form of the object, this will cause the software to give wrong results or behave unexpectedly.
 
(127, 13): methods should not be empty.
Its critical code smell.
Maintainability: it will make it difficult for developer to understand the need of the method when its empty which will make it hard to maintain the code whenever its needed.

-------------------------------------------------
Q4:
False Positive: SonarQube flags a line of code as having too high of a complexity score when in fact the code is well-structured and easily maintainable. This is a false positive because the complexity score does not accurately reflect the quality of the code.

The complexity score that SonarQube uses to identify complex code is based on a set of rules that can be adjusted to suit specific project requirements. For example, a complex method may be flagged as a potential issue, but if the method is well-organized, clearly documented, and performs its intended function effectively, it may not actually be a problem.


False Negative: SonarQube misses a potential logic error in a line of code that could cause incorrect behavior or results. This is a false negative because logic errors can be difficult to detect and can cause serious problems in the code.

SonarQube relies on static code analysis to identify potential issues, and it may miss logic errors that require dynamic testing to uncover. This is because dynamic testing involves executing the code and observing its behavior in real-time, while static analysis examines the code's structure and syntax without actually running it.
















