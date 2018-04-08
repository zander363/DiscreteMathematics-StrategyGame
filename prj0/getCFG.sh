#! /bin/sh
#
# getCFG.sh
# Copyright (C) 2018  <@DESKTOP-TA60DPH>
#
# Distributed under terms of the MIT license.
#



# curl https://github.com/blackberry/Wesnoth/tree/master/data/core/units/humans 

curl https://raw.githubusercontent.com/blackberry/Wesnoth/master/data/core/terrain.cfg -o terrain.cfg

curl https://raw.githubusercontent.com/blackberry/Wesnoth/master/data/core/units.cfg -o units.cfg
