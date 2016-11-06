# Lindenmayer-System
##Description
A java code that draws random graphics using System-L. In general, it's used to draw graphics that look like plants. The L-System (Lindenmayer) was designed for this purpose. It allows modeling some plants structures.

An L-System is, in fact, a formal grammar that defines the generation of Strings on alphabet. In our case, we use the alphabet Ff+[]X. The system is specified for the starting string (w) and a set of rules as (Char -> String ).


    Ex: W-> F
    F-> FF-F
    
In such a system, we generate links S0,S1,S2 by applying the rules of replacement of all the characters of Si to arrive to Si+1.


    S0= w
    F => FF-F => FF-FFF-F-FF-F
    
    
## turtle graphics


