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

#ruby_block "disable-service5d77b98b-f6ad-4e03-9033-ee27218c2217" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service5d77b98b-f6ad-4e03-9033-ee27218c2217::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['5d77b98b-f6ad-4e03-9033-ee27218c2217']['InstallationDir']}/service-5d77b98b-f6ad-4e03-9033-ee27218c2217.jar]", :immediately
#end

ruby_block "remove-service5d77b98b-f6ad-4e03-9033-ee27218c2217" do
  block do
  	node.run_list.remove("recipe[service5d77b98b-f6ad-4e03-9033-ee27218c2217::jar]")
  end
  only_if { node.run_list.include?("recipe[service5d77b98b-f6ad-4e03-9033-ee27218c2217::jar]") }
end

ruby_block "remove-deactivate-service5d77b98b-f6ad-4e03-9033-ee27218c2217" do
  block do
    node.run_list.remove("recipe[deactivate-service5d77b98b-f6ad-4e03-9033-ee27218c2217]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service5d77b98b-f6ad-4e03-9033-ee27218c2217]") }
end
