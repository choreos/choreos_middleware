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

ruby_block "remove-service7b16bc70-156c-4e34-b507-4efffe68556e" do
  block do
  	node.run_list.remove("recipe[service7b16bc70-156c-4e34-b507-4efffe68556e::jar]")
  end
  only_if { node.run_list.include?("recipe[service7b16bc70-156c-4e34-b507-4efffe68556e::jar]") }
end

ruby_block "remove-deactivate-service7b16bc70-156c-4e34-b507-4efffe68556e" do
  block do
    node.run_list.remove("recipe[deactivate-service7b16bc70-156c-4e34-b507-4efffe68556e]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service7b16bc70-156c-4e34-b507-4efffe68556e]") }
  notifies :stop, "service[service_7b16bc70-156c-4e34-b507-4efffe68556e_jar]", :immediately
end
