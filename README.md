#2013 SoCal ACM ICPC
##Problem 7: SPECTRUM

Swamp County Consulting has just won a contract from a mysterious Government Agency to build a database to investigate connections between what they call “targets.” Your team has been sub-contracted to create the query engine for this system. You are to report on targets, connections between targets, and the hop counts between connections as these terms are defined below.

> A “target” is represented by a string of up to 32 printing characters with no embedded spaces.
>
> A “connection” is a bi-directional relationship between two targets.
>
> The “hop count” between a given target (referred to here as “*target1*”) and another target is determined by the following rules:
>
> *	Targets directly connected to *target1* are 0 hops away.
> *	Targets directly connected to the 0 hop targets, and not already counted as a 0 hop target, are 1 hop targets.
> *	Similarly, targets directly connected to the *n* hop targets, and not already counted as 0 through n hop targets are *n + 1* hop targets.
> *	Targets are not treated as directly or indirectly connected to themselves.

The query engine is to have only three commands: <code>add</code>, <code>associated</code>, and <code>connections</code>. Targets and connections are never deleted because the Agency never forgets or makes mistakes.

Commands start in the first column of a line. Commands and their parameters are separated by whitespace. Input lines are at most 80 characters long. The input is terminated by end-of-file.

The commands are defined as follows:

**<code>add *target1*</code>**

One-parameter form of add command:

*	If target is not yet in the database, add it with no connections.
*	If target is already in the database do nothing (this is not an error).

**<code>add *target1* *target2*</code>**

Two-parameter form of add command:

Create a bidirectional connection between the targets. There can be at most one direct connection between targets.

*	If either target is not yet in the database, add it/them, and create the connection.
*	If there is already a connection between the targets, do nothing (this is not an error).
*	If *target1* and *target2* are the same, treat this as if the command was “add *target1*” (this is also not an error).
 
**<code>connections *target*</code>**

Command to print the number of hops to direct and indirect connections from the target:

Beginning with hop count 0, print the hop count, a colon, a single space, and the number of targets with that hop count with no leading spaces on a separate line. Continue by incrementing the hop count by one, ending with the hop count with the last non-zero number of targets.

*	If the target has no connections print a line containing only the string “no connections”.
*	If the target is not in the database print a line containing only the string “target does not exist”.

**<code>associated *target1* *target2*</code>**

Command to print information about the existence of a connection between the two targets:

If there is a path between the targets print “yes: *n*” on a separate line where *n* is the hop count of *target2* with respect to *target1*. Print one space after the colon with no leading zeroes and no trailing spaces.

*	If there is no connection between the targets, print “no” on a separate line.
*	If either *target1* or *target2* is not in the database, print a line containing only the string “target does not exist”.

The input to be processed will have fewer than 800,000 lines.
