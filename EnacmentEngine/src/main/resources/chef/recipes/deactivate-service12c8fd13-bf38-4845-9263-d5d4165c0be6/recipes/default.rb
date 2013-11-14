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

#ruby_block "disable-service12c8fd13-bf38-4845-9263-d5d4165c0be6" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service12c8fd13-bf38-4845-9263-d5d4165c0be6::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['12c8fd13-bf38-4845-9263-d5d4165c0be6']['InstallationDir']}/service-12c8fd13-bf38-4845-9263-d5d4165c0be6.jar]", :immediately
#end

ruby_block "remove-service12c8fd13-bf38-4845-9263-d5d4165c0be6" do
  block do
  	node.run_list.remove("recipe[service12c8fd13-bf38-4845-9263-d5d4165c0be6::jar]")
  end
  only_if { node.run_list.include?("recipe[service12c8fd13-bf38-4845-9263-d5d4165c0be6::jar]") }
end

ruby_block "remove-deactivate-service12c8fd13-bf38-4845-9263-d5d4165c0be6" do
  block do
    node.run_list.remove("recipe[deactivate-service12c8fd13-bf38-4845-9263-d5d4165c0be6]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service12c8fd13-bf38-4845-9263-d5d4165c0be6]") }
end
