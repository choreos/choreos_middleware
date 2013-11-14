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

ruby_block "disable-service09105506-a66a-49d3-acea-aa373243ed52" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service09105506-a66a-49d3-acea-aa373243ed52::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['09105506-a66a-49d3-acea-aa373243ed52']['InstallationDir']}/service-09105506-a66a-49d3-acea-aa373243ed52.jar]", :immediately
end

ruby_block "remove-service09105506-a66a-49d3-acea-aa373243ed52" do
  block do
  	node.run_list.remove("recipe[service09105506-a66a-49d3-acea-aa373243ed52::jar]")
  end
  only_if { node.run_list.include?("recipe[service09105506-a66a-49d3-acea-aa373243ed52::jar]") }
end

ruby_block "remove-deactivate-service09105506-a66a-49d3-acea-aa373243ed52" do
  block do
    node.run_list.remove("recipe[deactivate-service09105506-a66a-49d3-acea-aa373243ed52]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service09105506-a66a-49d3-acea-aa373243ed52]") }
end
