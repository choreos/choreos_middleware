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

#ruby_block "disable-service4520204d-db14-4404-ac84-4018eeb8d145" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service4520204d-db14-4404-ac84-4018eeb8d145::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['4520204d-db14-4404-ac84-4018eeb8d145']['InstallationDir']}/service-4520204d-db14-4404-ac84-4018eeb8d145.jar]", :immediately
#end

ruby_block "remove-service4520204d-db14-4404-ac84-4018eeb8d145" do
  block do
  	node.run_list.remove("recipe[service4520204d-db14-4404-ac84-4018eeb8d145::jar]")
  end
  only_if { node.run_list.include?("recipe[service4520204d-db14-4404-ac84-4018eeb8d145::jar]") }
end

ruby_block "remove-deactivate-service4520204d-db14-4404-ac84-4018eeb8d145" do
  block do
    node.run_list.remove("recipe[deactivate-service4520204d-db14-4404-ac84-4018eeb8d145]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service4520204d-db14-4404-ac84-4018eeb8d145]") }
end
