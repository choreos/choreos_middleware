Description
===========

Installs Glimpse and sets up the monitoring node that will be used for Enactment Engine.

Requirements
============

## Platform:

Ubuntu 10.04 or greater. Should work on any Debian family distributions.

## Cookbooks:

* activemq

Attributes
==========

* `node['glimpse']['tarball_url']` - Tarball URL to download Glimpse.

Usage
=====

Simply add `recipe[glimpse]` to a run list.

License and Author
==================

Author:: Thiago Furtado (<tfurtado@ime.usp.br>)

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at http://mozilla.org/MPL/2.0/.
