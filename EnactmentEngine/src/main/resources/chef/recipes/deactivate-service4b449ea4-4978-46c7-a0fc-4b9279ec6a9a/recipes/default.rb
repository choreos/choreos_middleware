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

#ruby_block "disable-service4b449ea4-4978-46c7-a0fc-4b9279ec6a9a" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service4b449ea4-4978-46c7-a0fc-4b9279ec6a9a::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['4b449ea4-4978-46c7-a0fc-4b9279ec6a9a']['InstallationDir']}/service-4b449ea4-4978-46c7-a0fc-4b9279ec6a9a.jar]", :immediately
#end

ruby_block "remove-service4b449ea4-4978-46c7-a0fc-4b9279ec6a9a" do
  block do
  	node.run_list.remove("recipe[service4b449ea4-4978-46c7-a0fc-4b9279ec6a9a::jar]")
  end
  only_if { node.run_list.include?("recipe[service4b449ea4-4978-46c7-a0fc-4b9279ec6a9a::jar]") }
end

ruby_block "remove-deactivate-service4b449ea4-4978-46c7-a0fc-4b9279ec6a9a" do
  block do
    node.run_list.remove("recipe[deactivate-service4b449ea4-4978-46c7-a0fc-4b9279ec6a9a]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service4b449ea4-4978-46c7-a0fc-4b9279ec6a9a]") }
end
