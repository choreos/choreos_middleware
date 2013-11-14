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

ruby_block "disable-service34bb1159-ba5d-477b-9dea-5a68b15ed9eb" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service34bb1159-ba5d-477b-9dea-5a68b15ed9eb::jar]") }
  notifies :stop, "service[service_34bb1159-ba5d-477b-9dea-5a68b15ed9eb_jar]", :immediately
end

ruby_block "remove-service34bb1159-ba5d-477b-9dea-5a68b15ed9eb" do
  block do
  	node.run_list.remove("recipe[service34bb1159-ba5d-477b-9dea-5a68b15ed9eb::jar]")
  end
  only_if { node.run_list.include?("recipe[service34bb1159-ba5d-477b-9dea-5a68b15ed9eb::jar]") }
end

ruby_block "remove-deactivate-service34bb1159-ba5d-477b-9dea-5a68b15ed9eb" do
  block do
    node.run_list.remove("recipe[deactivate-service34bb1159-ba5d-477b-9dea-5a68b15ed9eb]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service34bb1159-ba5d-477b-9dea-5a68b15ed9eb]") }
end
