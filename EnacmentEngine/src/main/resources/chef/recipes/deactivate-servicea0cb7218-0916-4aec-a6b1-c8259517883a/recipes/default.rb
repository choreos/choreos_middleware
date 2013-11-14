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

ruby_block "disable-servicea0cb7218-0916-4aec-a6b1-c8259517883a" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicea0cb7218-0916-4aec-a6b1-c8259517883a::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['a0cb7218-0916-4aec-a6b1-c8259517883a']['InstallationDir']}/service-a0cb7218-0916-4aec-a6b1-c8259517883a.jar]", :immediately
end

ruby_block "remove-servicea0cb7218-0916-4aec-a6b1-c8259517883a" do
  block do
  	node.run_list.remove("recipe[servicea0cb7218-0916-4aec-a6b1-c8259517883a::jar]")
  end
  only_if { node.run_list.include?("recipe[servicea0cb7218-0916-4aec-a6b1-c8259517883a::jar]") }
end

ruby_block "remove-deactivate-servicea0cb7218-0916-4aec-a6b1-c8259517883a" do
  block do
    node.run_list.remove("recipe[deactivate-servicea0cb7218-0916-4aec-a6b1-c8259517883a]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicea0cb7218-0916-4aec-a6b1-c8259517883a]") }
end
