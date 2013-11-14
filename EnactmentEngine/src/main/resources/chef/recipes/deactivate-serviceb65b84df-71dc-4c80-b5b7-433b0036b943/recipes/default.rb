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

ruby_block "disable-serviceb65b84df-71dc-4c80-b5b7-433b0036b943" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceb65b84df-71dc-4c80-b5b7-433b0036b943::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['b65b84df-71dc-4c80-b5b7-433b0036b943']['InstallationDir']}/service-b65b84df-71dc-4c80-b5b7-433b0036b943.jar]", :immediately
end

ruby_block "remove-serviceb65b84df-71dc-4c80-b5b7-433b0036b943" do
  block do
  	node.run_list.remove("recipe[serviceb65b84df-71dc-4c80-b5b7-433b0036b943::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceb65b84df-71dc-4c80-b5b7-433b0036b943::jar]") }
end

ruby_block "remove-deactivate-serviceb65b84df-71dc-4c80-b5b7-433b0036b943" do
  block do
    node.run_list.remove("recipe[deactivate-serviceb65b84df-71dc-4c80-b5b7-433b0036b943]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceb65b84df-71dc-4c80-b5b7-433b0036b943]") }
end
