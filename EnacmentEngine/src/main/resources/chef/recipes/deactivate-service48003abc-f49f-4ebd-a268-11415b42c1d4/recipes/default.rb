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

#ruby_block "disable-service48003abc-f49f-4ebd-a268-11415b42c1d4" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service48003abc-f49f-4ebd-a268-11415b42c1d4::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['48003abc-f49f-4ebd-a268-11415b42c1d4']['InstallationDir']}/service-48003abc-f49f-4ebd-a268-11415b42c1d4.jar]", :immediately
#end

ruby_block "remove-service48003abc-f49f-4ebd-a268-11415b42c1d4" do
  block do
  	node.run_list.remove("recipe[service48003abc-f49f-4ebd-a268-11415b42c1d4::jar]")
  end
  only_if { node.run_list.include?("recipe[service48003abc-f49f-4ebd-a268-11415b42c1d4::jar]") }
end

ruby_block "remove-deactivate-service48003abc-f49f-4ebd-a268-11415b42c1d4" do
  block do
    node.run_list.remove("recipe[deactivate-service48003abc-f49f-4ebd-a268-11415b42c1d4]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service48003abc-f49f-4ebd-a268-11415b42c1d4]") }
end
