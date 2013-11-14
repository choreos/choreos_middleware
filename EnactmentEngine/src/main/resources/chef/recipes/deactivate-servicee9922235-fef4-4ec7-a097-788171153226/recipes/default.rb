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

ruby_block "disable-servicee9922235-fef4-4ec7-a097-788171153226" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicee9922235-fef4-4ec7-a097-788171153226::jar]") }
  notifies :stop, "service[service_e9922235-fef4-4ec7-a097-788171153226_jar]", :immediately
end

ruby_block "remove-servicee9922235-fef4-4ec7-a097-788171153226" do
  block do
  	node.run_list.remove("recipe[servicee9922235-fef4-4ec7-a097-788171153226::jar]")
  end
  only_if { node.run_list.include?("recipe[servicee9922235-fef4-4ec7-a097-788171153226::jar]") }
end

ruby_block "remove-deactivate-servicee9922235-fef4-4ec7-a097-788171153226" do
  block do
    node.run_list.remove("recipe[deactivate-servicee9922235-fef4-4ec7-a097-788171153226]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicee9922235-fef4-4ec7-a097-788171153226]") }
end
