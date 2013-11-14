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

#ruby_block "disable-service158adc04-6b77-4642-84ba-16d32a9213e1" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service158adc04-6b77-4642-84ba-16d32a9213e1::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['158adc04-6b77-4642-84ba-16d32a9213e1']['InstallationDir']}/service-158adc04-6b77-4642-84ba-16d32a9213e1.jar]", :immediately
#end

ruby_block "remove-service158adc04-6b77-4642-84ba-16d32a9213e1" do
  block do
  	node.run_list.remove("recipe[service158adc04-6b77-4642-84ba-16d32a9213e1::jar]")
  end
  only_if { node.run_list.include?("recipe[service158adc04-6b77-4642-84ba-16d32a9213e1::jar]") }
end

ruby_block "remove-deactivate-service158adc04-6b77-4642-84ba-16d32a9213e1" do
  block do
    node.run_list.remove("recipe[deactivate-service158adc04-6b77-4642-84ba-16d32a9213e1]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service158adc04-6b77-4642-84ba-16d32a9213e1]") }
end
