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

#ruby_block "disable-service50672d2b-725c-4baf-ba99-973fee9a7943" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service50672d2b-725c-4baf-ba99-973fee9a7943::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['50672d2b-725c-4baf-ba99-973fee9a7943']['InstallationDir']}/service-50672d2b-725c-4baf-ba99-973fee9a7943.jar]", :immediately
#end

ruby_block "remove-service50672d2b-725c-4baf-ba99-973fee9a7943" do
  block do
  	node.run_list.remove("recipe[service50672d2b-725c-4baf-ba99-973fee9a7943::jar]")
  end
  only_if { node.run_list.include?("recipe[service50672d2b-725c-4baf-ba99-973fee9a7943::jar]") }
end

ruby_block "remove-deactivate-service50672d2b-725c-4baf-ba99-973fee9a7943" do
  block do
    node.run_list.remove("recipe[deactivate-service50672d2b-725c-4baf-ba99-973fee9a7943]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service50672d2b-725c-4baf-ba99-973fee9a7943]") }
end
