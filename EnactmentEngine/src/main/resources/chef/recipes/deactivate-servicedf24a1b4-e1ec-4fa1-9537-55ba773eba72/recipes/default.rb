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

#ruby_block "disable-servicedf24a1b4-e1ec-4fa1-9537-55ba773eba72" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[servicedf24a1b4-e1ec-4fa1-9537-55ba773eba72::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['df24a1b4-e1ec-4fa1-9537-55ba773eba72']['InstallationDir']}/service-df24a1b4-e1ec-4fa1-9537-55ba773eba72.jar]", :immediately
#end

ruby_block "remove-servicedf24a1b4-e1ec-4fa1-9537-55ba773eba72" do
  block do
  	node.run_list.remove("recipe[servicedf24a1b4-e1ec-4fa1-9537-55ba773eba72::jar]")
  end
  only_if { node.run_list.include?("recipe[servicedf24a1b4-e1ec-4fa1-9537-55ba773eba72::jar]") }
end

ruby_block "remove-deactivate-servicedf24a1b4-e1ec-4fa1-9537-55ba773eba72" do
  block do
    node.run_list.remove("recipe[deactivate-servicedf24a1b4-e1ec-4fa1-9537-55ba773eba72]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicedf24a1b4-e1ec-4fa1-9537-55ba773eba72]") }
end
