Email Classifier.
This is a raw page that will be heavily edited.

This is an attempt to classify office email. The 'result' or 'match' of an email is either urgent, or important

This is targeted at office email, taking into account (not in this order):

1) Org structure

2) 'Spam' from builders, or mailing lists

3) People that have been responded to earlier

4) Previously read email

5) Calendar invites

6) Dates => if an important, actionable 'thing' hasnt been addressed, it should bubble to the top, irritating the mail user.


It will use font size, colors, sort order and swimlanes to bug the email user. (Bug => Annoy, pester by using these visual artifices)

It is expected that the user has a working account that has this information previously.

I'll be using clojure and chronicling my learnings.
The first learning is to break this down into 'wishful thinking'. I had one run of programming without this wishful thinking paradigm and it all turned into spoilt sphagetti very soon. The problem is one of mental modeling: I subconsciously tried creating 'objects' or types that interact with each other and somehow that goes against the grain of the language. As I remodeled them, it got progressively ugly as I was getting reluctant to throw away the work I did, the pieces that were working, the THINGS.

Now instead of beleiving that clojure does not work for big projects with multiple ideas, I'll try and  think of ACTIONS.

The first attempt is : 
;;wishful thinking :
(write-to-db
  (convert-to-json
    (get-from-server (get-last-message-written-in-db))))

    
    
