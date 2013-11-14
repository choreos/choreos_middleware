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

#ruby_block "disable-serviced7c473f2-4b47-408a-9820-1a1b19450865" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[serviced7c473f2-4b47-408a-9820-1a1b19450865::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['d7c473f2-4b47-408a-9820-1a1b19450865']['InstallationDir']}/service-d7c473f2-4b47-408a-9820-1a1b19450865.jar]", :immediately
#end

ruby_block "remove-serviced7c473f2-4b47-408a-9820-1a1b19450865" do
  block do
  	node.run_list.remove("recipe[serviced7c473f2-4b47-408a-9820-1a1b19450865::jar]")
  end
  only_if { node.run_list.include?("recipe[serviced7c473f2-4b47-408a-9820-1a1b19450865::jar]") }
end

ruby_block "remove-deactivate-serviced7c473f2-4b47-408a-9820-1a1b19450865" do
  block do
    node.run_list.remove("recipe[deactivate-serviced7c473f2-4b47-408a-9820-1a1b19450865]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviced7c473f2-4b47-408a-9820-1a1b19450865]") }
end
