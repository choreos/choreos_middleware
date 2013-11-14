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

#ruby_block "disable-servicec9e23f85-bcb8-4b6f-8f7f-db5a8259f71b" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[servicec9e23f85-bcb8-4b6f-8f7f-db5a8259f71b::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['c9e23f85-bcb8-4b6f-8f7f-db5a8259f71b']['InstallationDir']}/service-c9e23f85-bcb8-4b6f-8f7f-db5a8259f71b.jar]", :immediately
#end

ruby_block "remove-servicec9e23f85-bcb8-4b6f-8f7f-db5a8259f71b" do
  block do
  	node.run_list.remove("recipe[servicec9e23f85-bcb8-4b6f-8f7f-db5a8259f71b::jar]")
  end
  only_if { node.run_list.include?("recipe[servicec9e23f85-bcb8-4b6f-8f7f-db5a8259f71b::jar]") }
end

ruby_block "remove-deactivate-servicec9e23f85-bcb8-4b6f-8f7f-db5a8259f71b" do
  block do
    node.run_list.remove("recipe[deactivate-servicec9e23f85-bcb8-4b6f-8f7f-db5a8259f71b]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicec9e23f85-bcb8-4b6f-8f7f-db5a8259f71b]") }
end
