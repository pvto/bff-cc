bff-cc + toolset
===============================
It is a toyish set of dsl tools that runs on Java.
Be warned: It's QUITE a work in progress.
Just for fun. Might or might not be useful in the future.


Some ideas (what for?): 
- quick parser implementations dynamically
- ad hoc DSLs (yes, what a lovely, lovely idea :)
- composable languages
- sharing of features and data between different DSLs

"This is Mostly working now, might evolve, more in the future":
- a mini language for syntax definition (See LexerTest)
- a mini cc (compiler compiler) (See LexerTest)
- consume regex (subset of) from streams (See StreamRegexTest)





Naming and other code conventions
===============================
Oracle has given out an amazing set of Java style instructions.
I don't use them.  You should use them.

What I do (acronymishly, tongue-in-cheek, as you like it):

SFOC - prefer Static Functions Over Classes
AEOAP - Almost Everything Open And Public
AGS - Avoid Getters and Setters wherever possible
DOWB - Determined Oddities With (curly) Brackets
MLTOSL - Many Little Things On a Single Line
TSOC - Terse and Short Over Conventional


Package names
----------------
bff          - the root package (it's short and lovely, isn't it?)
             Remark: This java package contains, for now, just some tests towards composability.
                     Not guaranteed to be fully worked out or anything else.

Classes
----------------
CamelCase is not required in class names, but here are some odd reservations.

_xxx.java     - classes starting with a  _  implement _  (being modules)
s_xx.java     - classes starting with   s   implement s_ (they are metasyntax nodes)
p_xx.java     - classes starting with   p_  implement p_ (they are parsers)
$xxx.java     - classes starting with a  $  implement $  (they are functions)

Methods
----------------
camelCase with initial lower letter is preferred in method names (I'm being nice here!)

xxx_()        - method names ending with a  _  must return the owning object (builder pattern)
static yyy_() - static methods ending with  _  must return the first argument or its descendant, if applicable