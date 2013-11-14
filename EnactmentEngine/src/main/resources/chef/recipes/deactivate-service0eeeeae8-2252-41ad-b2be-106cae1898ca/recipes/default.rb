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

#ruby_block "disable-service0eeeeae8-2252-41ad-b2be-106cae1898ca" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service0eeeeae8-2252-41ad-b2be-106cae1898ca::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['0eeeeae8-2252-41ad-b2be-106cae1898ca']['InstallationDir']}/service-0eeeeae8-2252-41ad-b2be-106cae1898ca.jar]", :immediately
#end

ruby_block "remove-service0eeeeae8-2252-41ad-b2be-106cae1898ca" do
  block do
  	node.run_list.remove("recipe[service0eeeeae8-2252-41ad-b2be-106cae1898ca::jar]")
  end
  only_if { node.run_list.include?("recipe[service0eeeeae8-2252-41ad-b2be-106cae1898ca::jar]") }
end

ruby_block "remove-deactivate-service0eeeeae8-2252-41ad-b2be-106cae1898ca" do
  block do
    node.run_list.remove("recipe[deactivate-service0eeeeae8-2252-41ad-b2be-106cae1898ca]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service0eeeeae8-2252-41ad-b2be-106cae1898ca]") }
end
