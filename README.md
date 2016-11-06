# Lindenmayer-System
##Description
A java code that draws random graphics using System-L. In general, it's used to draw graphics that look like plants. The L-System (Lindenmayer) was designed for this purpose. It allows modeling some plants structures.

An L-System is, in fact, a formal grammar that defines the generation of Strings on alphabet. In our case, we use the alphabet Ff+[]X. The system is specified for the starting string (w) and a set of rules as (Char -> String ).


    Ex: W-> F
    F-> FF-F
    
In such a system, we generate links S0,S1,S2 by applying the rules of replacement of all the characters of Si to arrive to Si+1.


    S0= w
    F => FF-F => FF-FFF-F-FF-F
    
    
    
![d266e651-61d8-4856-878f-d0a93d0f4df7](https://cloud.githubusercontent.com/assets/14367775/20034767/92b3fb8c-a3a0-11e6-89b3-5b48c5050d04.jpg)
## turtle graphics

The turtle contains a pen and can move forward (Fixed distance D) while drawing a line ( Symbol F ) or not ( Symbol f ).
The turtle can also rotate (fixed angel Omega) in the same direction of a clock ( Symbol - ) or not ( Symbol + ). The state of the turtle has a position of (x,y) and also an angel ϴ compared to the horizontal line. A graphic is specified by an Array of Chars in L-System. We interpret one by one each character.

        F : Move the turtle forward by D, while drawing a line from the depart position to the arriving one. The state of the turtle changes from (x,y,ϴ) to (x+ Dcosϴ, y+Dsinϴ, ϴ) Or D un a global parametre.
        
        f: Move the turtle forward without drawing a line. The state of the turtle has the same behavior mentioned above.
        + : Rotates the head of the turtle. The state of the turtle changes from (x,y,ϴ) to (x,y,ϴ+Omega)
        - : Rotates the head of the turtle. The state of the turtle changes from (x,y,ϴ) to (x,y,ϴ-Omega)
        [ : pushes the current state of the turtle to the saved states stack. Current state does not change.
        ] : pops  the state of the turtle and affects the current state. Which means the staet changes from (x,y,ϴ) to (x',y',ϴ'). (x',y',ϴ') is recently saved state by [.
        X : no affection.


