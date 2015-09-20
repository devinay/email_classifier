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

Will be using clojure
