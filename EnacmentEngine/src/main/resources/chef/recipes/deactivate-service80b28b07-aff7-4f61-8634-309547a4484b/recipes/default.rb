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

ruby_block "disable-service80b28b07-aff7-4f61-8634-309547a4484b" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service80b28b07-aff7-4f61-8634-309547a4484b::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['80b28b07-aff7-4f61-8634-309547a4484b']['InstallationDir']}/service-80b28b07-aff7-4f61-8634-309547a4484b.jar]", :immediately
end

ruby_block "remove-service80b28b07-aff7-4f61-8634-309547a4484b" do
  block do
  	node.run_list.remove("recipe[service80b28b07-aff7-4f61-8634-309547a4484b::jar]")
  end
  only_if { node.run_list.include?("recipe[service80b28b07-aff7-4f61-8634-309547a4484b::jar]") }
end

ruby_block "remove-deactivate-service80b28b07-aff7-4f61-8634-309547a4484b" do
  block do
    node.run_list.remove("recipe[deactivate-service80b28b07-aff7-4f61-8634-309547a4484b]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service80b28b07-aff7-4f61-8634-309547a4484b]") }
end
