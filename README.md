# SetTheGoal
This project aims to help people to set their goals.

Setup: MVVM, Clean Arch, Coroutines, Single activity application.

Application supports two languages: English and Russian.

Interesting features:
1.Background syntactic analysis of input text 
2.Background translation russian key-words 

Why do I use background syntactic analysis and translation?
Using syntactic analysis I extract key-words from user's goal. 
Key-words I meant parts of speech: nouns, verbs, adjectives, adverbs.
It needs to create appropriate suggestions for picture search.
To find appropriate picture which suits for user's goal I use https://unsplash.com search api.
Background translation needs to get search opportunity in unsplash api. It works only with eng lang.

How do suggestions work?
1.User writes his/her goal.
2.User press Next button to save a goal.
3.The app sends a request to get parts of speech and gets a response.
4.If it is a russian lang, translation executes.
5.After it User can see a list of suggestions.
    For example:
    Input goal: I want to give an opportunity to travel for my family.
    Output suggestions: opportunity, family, want, give, travel.
6.Using suggestions (opportunity, family, want, give, travel) it's easy to find appropriate picture for user's goal.
7.User presses on, for example, "travel" and gets a list of pictures related to traveling.