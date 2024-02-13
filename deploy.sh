#!/bin/bash

today = `date +%m-%d-%Y`

# git add . && git commit -m "Deploy to dokku:acont in ~> " && git push -f dokku main && git push -f origin main
git add . && git commit -m "Deploy to dokku:acont:" && git push -f dokku acont:main && git push origin acont
