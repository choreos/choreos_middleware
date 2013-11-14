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

#ruby_block "disable-service59632629-621c-48d2-9fe0-d562adbdffa2" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service59632629-621c-48d2-9fe0-d562adbdffa2::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['59632629-621c-48d2-9fe0-d562adbdffa2']['InstallationDir']}/service-59632629-621c-48d2-9fe0-d562adbdffa2.jar]", :immediately
#end

ruby_block "remove-service59632629-621c-48d2-9fe0-d562adbdffa2" do
  block do
  	node.run_list.remove("recipe[service59632629-621c-48d2-9fe0-d562adbdffa2::jar]")
  end
  only_if { node.run_list.include?("recipe[service59632629-621c-48d2-9fe0-d562adbdffa2::jar]") }
end

ruby_block "remove-deactivate-service59632629-621c-48d2-9fe0-d562adbdffa2" do
  block do
    node.run_list.remove("recipe[deactivate-service59632629-621c-48d2-9fe0-d562adbdffa2]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service59632629-621c-48d2-9fe0-d562adbdffa2]") }
end
