#!/bin/bash
#
# Author: Cadu
# Description: This script resets a node run list.
# Requirements: knife and .conf (template available).

DIR=$(dirname $0)
CONF="$DIR/run_list_reset.conf"

if ! test -e "$CONF"; then
  echo "Create $CONF from $CONF.template"
  exit 0
fi

. "$CONF"

echo 'Clearing current run list...'
CURRENT_RUN_LIST=$(knife node show $NODE -a run_list | cut -f2 -d\: | sed 's/,//g')

for item in $CURRENT_RUN_LIST; do
  CMD="knife node run_list remove $NODE $item"
  echo $CMD
  $CMD >/dev/null
done

echo -e "\nRun list should be empty:"
knife node show $NODE -a run_list

echo -e "\nAdding item(s) in run list: '$FINAL_RUN_LIST'"
for item in $FINAL_RUN_LIST; do
  CMD="knife node run_list add $NODE $item"
  echo $CMD
  $CMD >/dev/null
done

echo -e "\nFinal run list:"
knife node show $NODE -a run_list
