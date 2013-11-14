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

ruby_block "disable-service24b66a24-0471-4ab4-b71f-d1c9403566a9" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service24b66a24-0471-4ab4-b71f-d1c9403566a9::jar]") }
  notifies :stop, "service[service_24b66a24-0471-4ab4-b71f-d1c9403566a9_jar]", :immediately
end

ruby_block "remove-service24b66a24-0471-4ab4-b71f-d1c9403566a9" do
  block do
  	node.run_list.remove("recipe[service24b66a24-0471-4ab4-b71f-d1c9403566a9::jar]")
  end
  only_if { node.run_list.include?("recipe[service24b66a24-0471-4ab4-b71f-d1c9403566a9::jar]") }
end

ruby_block "remove-deactivate-service24b66a24-0471-4ab4-b71f-d1c9403566a9" do
  block do
    node.run_list.remove("recipe[deactivate-service24b66a24-0471-4ab4-b71f-d1c9403566a9]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service24b66a24-0471-4ab4-b71f-d1c9403566a9]") }
end
