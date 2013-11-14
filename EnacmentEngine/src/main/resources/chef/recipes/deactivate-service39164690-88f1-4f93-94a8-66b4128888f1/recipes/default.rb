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

#ruby_block "disable-service39164690-88f1-4f93-94a8-66b4128888f1" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service39164690-88f1-4f93-94a8-66b4128888f1::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['39164690-88f1-4f93-94a8-66b4128888f1']['InstallationDir']}/service-39164690-88f1-4f93-94a8-66b4128888f1.jar]", :immediately
#end

ruby_block "remove-service39164690-88f1-4f93-94a8-66b4128888f1" do
  block do
  	node.run_list.remove("recipe[service39164690-88f1-4f93-94a8-66b4128888f1::jar]")
  end
  only_if { node.run_list.include?("recipe[service39164690-88f1-4f93-94a8-66b4128888f1::jar]") }
end

ruby_block "remove-deactivate-service39164690-88f1-4f93-94a8-66b4128888f1" do
  block do
    node.run_list.remove("recipe[deactivate-service39164690-88f1-4f93-94a8-66b4128888f1]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service39164690-88f1-4f93-94a8-66b4128888f1]") }
end
