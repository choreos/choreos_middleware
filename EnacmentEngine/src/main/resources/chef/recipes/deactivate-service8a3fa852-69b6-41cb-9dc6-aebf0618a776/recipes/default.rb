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

#ruby_block "disable-service8a3fa852-69b6-41cb-9dc6-aebf0618a776" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service8a3fa852-69b6-41cb-9dc6-aebf0618a776::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['8a3fa852-69b6-41cb-9dc6-aebf0618a776']['InstallationDir']}/service-8a3fa852-69b6-41cb-9dc6-aebf0618a776.jar]", :immediately
#end

ruby_block "remove-service8a3fa852-69b6-41cb-9dc6-aebf0618a776" do
  block do
  	node.run_list.remove("recipe[service8a3fa852-69b6-41cb-9dc6-aebf0618a776::jar]")
  end
  only_if { node.run_list.include?("recipe[service8a3fa852-69b6-41cb-9dc6-aebf0618a776::jar]") }
end

ruby_block "remove-deactivate-service8a3fa852-69b6-41cb-9dc6-aebf0618a776" do
  block do
    node.run_list.remove("recipe[deactivate-service8a3fa852-69b6-41cb-9dc6-aebf0618a776]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service8a3fa852-69b6-41cb-9dc6-aebf0618a776]") }
end
