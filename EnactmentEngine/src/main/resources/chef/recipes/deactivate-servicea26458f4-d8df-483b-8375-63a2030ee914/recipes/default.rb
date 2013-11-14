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

ruby_block "disable-servicea26458f4-d8df-483b-8375-63a2030ee914" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicea26458f4-d8df-483b-8375-63a2030ee914::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['a26458f4-d8df-483b-8375-63a2030ee914']['InstallationDir']}/service-a26458f4-d8df-483b-8375-63a2030ee914.jar]", :immediately
end

ruby_block "remove-servicea26458f4-d8df-483b-8375-63a2030ee914" do
  block do
  	node.run_list.remove("recipe[servicea26458f4-d8df-483b-8375-63a2030ee914::jar]")
  end
  only_if { node.run_list.include?("recipe[servicea26458f4-d8df-483b-8375-63a2030ee914::jar]") }
end

ruby_block "remove-deactivate-servicea26458f4-d8df-483b-8375-63a2030ee914" do
  block do
    node.run_list.remove("recipe[deactivate-servicea26458f4-d8df-483b-8375-63a2030ee914]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicea26458f4-d8df-483b-8375-63a2030ee914]") }
end
