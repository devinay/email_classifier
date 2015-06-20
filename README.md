Email Classifier.
This is a raw page that will be heavily edited.

This is an attempt to classify office email. The 'result' or 'match' of an email is either urgent, or important.

This is targeted at office email, taking into account (not in this order):
1) Org structure
2) 'Spam' from builders, or mailing lists
3) People that have been responded to earlier
4) Previously read email
5) Calendar invites
6) Dates => if an important, actionable 'thing' hasnt been addressed, it should bubble to the top, irritating the mail user.

it uses bold font, colors and sort order to

It is expected that the user has a working account that has this information previously.

I have this horrbile problem where I decide to reply to some email and it slips my mind. This comee back and bites me later. I come off as incompetent for not staying in touch with email. This is an attempt to use automation to encode my brain rules into software.

Will be using either scala or clojure => TBD
