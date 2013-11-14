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

ruby_block "disable-service5f436c2e-527d-4cea-8c69-299a2f3b6fed" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service5f436c2e-527d-4cea-8c69-299a2f3b6fed::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['5f436c2e-527d-4cea-8c69-299a2f3b6fed']['InstallationDir']}/service-5f436c2e-527d-4cea-8c69-299a2f3b6fed.jar]", :immediately
end

ruby_block "remove-service5f436c2e-527d-4cea-8c69-299a2f3b6fed" do
  block do
  	node.run_list.remove("recipe[service5f436c2e-527d-4cea-8c69-299a2f3b6fed::jar]")
  end
  only_if { node.run_list.include?("recipe[service5f436c2e-527d-4cea-8c69-299a2f3b6fed::jar]") }
end

ruby_block "remove-deactivate-service5f436c2e-527d-4cea-8c69-299a2f3b6fed" do
  block do
    node.run_list.remove("recipe[deactivate-service5f436c2e-527d-4cea-8c69-299a2f3b6fed]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service5f436c2e-527d-4cea-8c69-299a2f3b6fed]") }
end
