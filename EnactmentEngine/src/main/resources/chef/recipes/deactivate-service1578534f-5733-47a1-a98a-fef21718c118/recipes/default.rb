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

#ruby_block "disable-service1578534f-5733-47a1-a98a-fef21718c118" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service1578534f-5733-47a1-a98a-fef21718c118::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['1578534f-5733-47a1-a98a-fef21718c118']['InstallationDir']}/service-1578534f-5733-47a1-a98a-fef21718c118.jar]", :immediately
#end

ruby_block "remove-service1578534f-5733-47a1-a98a-fef21718c118" do
  block do
  	node.run_list.remove("recipe[service1578534f-5733-47a1-a98a-fef21718c118::jar]")
  end
  only_if { node.run_list.include?("recipe[service1578534f-5733-47a1-a98a-fef21718c118::jar]") }
end

ruby_block "remove-deactivate-service1578534f-5733-47a1-a98a-fef21718c118" do
  block do
    node.run_list.remove("recipe[deactivate-service1578534f-5733-47a1-a98a-fef21718c118]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service1578534f-5733-47a1-a98a-fef21718c118]") }
end
