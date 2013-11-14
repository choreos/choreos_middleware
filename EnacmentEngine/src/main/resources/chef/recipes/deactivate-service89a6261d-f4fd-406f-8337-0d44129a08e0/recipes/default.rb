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

#ruby_block "disable-service89a6261d-f4fd-406f-8337-0d44129a08e0" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service89a6261d-f4fd-406f-8337-0d44129a08e0::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['89a6261d-f4fd-406f-8337-0d44129a08e0']['InstallationDir']}/service-89a6261d-f4fd-406f-8337-0d44129a08e0.jar]", :immediately
#end

ruby_block "remove-service89a6261d-f4fd-406f-8337-0d44129a08e0" do
  block do
  	node.run_list.remove("recipe[service89a6261d-f4fd-406f-8337-0d44129a08e0::jar]")
  end
  only_if { node.run_list.include?("recipe[service89a6261d-f4fd-406f-8337-0d44129a08e0::jar]") }
end

ruby_block "remove-deactivate-service89a6261d-f4fd-406f-8337-0d44129a08e0" do
  block do
    node.run_list.remove("recipe[deactivate-service89a6261d-f4fd-406f-8337-0d44129a08e0]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service89a6261d-f4fd-406f-8337-0d44129a08e0]") }
end
