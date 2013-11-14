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

#ruby_block "disable-servicef690b0fc-c456-4ba9-9e00-e2f79cfd15e0" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[servicef690b0fc-c456-4ba9-9e00-e2f79cfd15e0::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['f690b0fc-c456-4ba9-9e00-e2f79cfd15e0']['InstallationDir']}/service-f690b0fc-c456-4ba9-9e00-e2f79cfd15e0.jar]", :immediately
#end

ruby_block "remove-servicef690b0fc-c456-4ba9-9e00-e2f79cfd15e0" do
  block do
  	node.run_list.remove("recipe[servicef690b0fc-c456-4ba9-9e00-e2f79cfd15e0::jar]")
  end
  only_if { node.run_list.include?("recipe[servicef690b0fc-c456-4ba9-9e00-e2f79cfd15e0::jar]") }
end

ruby_block "remove-deactivate-servicef690b0fc-c456-4ba9-9e00-e2f79cfd15e0" do
  block do
    node.run_list.remove("recipe[deactivate-servicef690b0fc-c456-4ba9-9e00-e2f79cfd15e0]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicef690b0fc-c456-4ba9-9e00-e2f79cfd15e0]") }
end
