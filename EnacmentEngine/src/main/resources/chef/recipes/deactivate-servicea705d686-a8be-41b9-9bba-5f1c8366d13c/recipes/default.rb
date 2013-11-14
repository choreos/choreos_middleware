#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  #
##########################################################################

ruby_block "remove-activate-servicea705d686-a8be-41b9-9bba-5f1c8366d13c" do
  block do
    node.run_list.remove("recipe[activate-servicea705d686-a8be-41b9-9bba-5f1c8366d13c]")
  end
  only_if { node.run_list.include?("recipe[activate-servicea705d686-a8be-41b9-9bba-5f1c8366d13c]") }
end


ruby_block "remove-deactivate-servicea705d686-a8be-41b9-9bba-5f1c8366d13c" do
  block do
    node.run_list.remove("recipe[deactivate-servicea705d686-a8be-41b9-9bba-5f1c8366d13c]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicea705d686-a8be-41b9-9bba-5f1c8366d13c]") }
  notifies :stop, "service[service_a705d686-a8be-41b9-9bba-5f1c8366d13c_jar]", :immediately
end
